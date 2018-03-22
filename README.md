# LASTSCREEN SDK for Android

* 라스트스크린을 안드로이드 어플리케이션에 연동하기 위한 라이브러리
* 안드로이드 버전 지원 : Android 2.3(API Level 9) 이상 (com.android.support:appcompat-v7 사용 필수)
* 연동을 하기 위해 발급받아야 하는 키
* `sdk_key` : 라스트스크린 담당자에게 발급 받아야합니다 (테스트 sdk_key : d67d8ab4f4c10bf22aa353e27879133c)


## 라스트스크린 SDK 연동 가이드 - 기본

### 1. 설정

#### lastscreen_sdk_x.x.zip 파일을 다운로드 합니다. 아래 3개 파일을 확인합니다
- lastscreen_x.x.jar : 라스트스크린 jar 파일
- lastscreen_activity.xml : 라스트스크린 레이아웃 파일
- lastscreen_close_ic.png : 라스트스크린 닫기버튼 이미지 파일


#### 안드로이드 프로젝트에 `lastscreen_x.x.jar` 을 import 합니다

#### `build.gradle` 설정
- `compile project(':lastscreen_x.x')` 추가
- `compile 'com.android.support:appcompat-v7:25.+'`추가

앱의 minSdkVersion이 14 미만인 경우 appcompat-v7:25 이하 버전으로 컴파일 해야 합니다. (appcompat-v7:26 이상버전은 minSdkVersion 14이상 지원)

#### `AndroidManifest.xml` 에 다음 코드를 추가합니다.
- 라스트스크린을 위한 퍼미션 설정
- 라스트스크린 광고를 호출하기 위한 설정
```Xml
<manifest>
  
    <!--S:LASTSCREEN 퍼미션 설정  -->
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!--E:LASTSCREEN 퍼미션 설정  -->
    
    <application>
        ...
        
  
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>


        <!--S: LASTSCREEN 설정 사항-->
        <activity android:name="com.tnplanet.lastscreen_sdk.AD.LastscreenActivity"/>
        <activity android:name="com.tnplanet.lastscreen_sdk.AD.LastscreenAvoidPatternActivity" />
        <!--E: LASTSCREEN 설정 사항 -->

    </application>
</manifest>
```

#### `lastscreen_activity.xml` 파일을 res > layout 폴더에 복사 합니다
파일이름과 구성요소 ID는 반드시 지켜져야 합니다

```Xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/lastscreen_view"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
    
</RelativeLayout>
```

#### `lastscreen_close_ic.png` 파일을 res > drawable 폴더에 복사 합니다
닫기버튼 이미지 파일입니다.


#### 프로가드 처리 `proguard-rules.pro` 에 다음 코드를 추가합니다.
```Xml
-ignorewarnings

-keep class com.tnplanet.lastscreen_sdk.AD.LastscreenActivity$WebAppInterface { *; }
-keep interface com.tnplanet.lastscreen_sdk.AD.LastscreenActivity$WebAppInterface { *; }
```



### 2. 라스트스크린 호출
- 라스트스크린을 호출하기 위해서는 `sdk_key`를 발급 받아야 합니다.

```Java
public class MainActivity extends AppCompatActivity {

    private LastscreenAD lastscreenAD;    //라스트스크린 정의

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        lastscreenAD = new LastscreenAD(MainActivity.this);
        lastscreenAD.setInitCallBack(new LastscreenAD.LastscreenInitCallBack() {
            @Override
            public void initCallBack(boolean valid, String msg) {   
                Log.d("MainActivity", "LastscreenCallBack : "+valid+" / "+msg);
                mainTv.setText(msg);
            }
        });
        
        lastscreenAD.setInitFinishCallBack(new LastscreenAD.LastscreenFinishCallBack() {
            @Override
            public void finishCallBack() {
                //광고 종료 콜백
            }
        });

        lastscreenAD.setActivityClose(true);    //광고 종료 후 현재 액티비티 종료 여부(default : true)
        lastscreenAD.init("sdk_key");           //광고 준비
    }

    @Override
    public void onBackPressed() {
        lastscreenAD.showAD();              //광고 Display(앱이 종료되는 시점)
    }
}
```

- 자사 라스트스크린 담당자에게 라스트스크린 노출 요청 승인이 되어야 실행 됩니다.
- 앱이 종료되는 시점에 showAD()를 호출하면 광고가 Display됩니다.
- setActivityClose(true / false) : 광고 종료 후 현재 액티비티 종료 여부 설정입니다. 디폴트값 true로 설정되어있습니다. LastscreenFinishCallBack 이 설정되어 있지 않은경우에만 유효 합니다.


- 콜백 메시지 

'success' : 광고 준비 완료

'undefined_key' : sdk_key 오류

'no_sdk_key' : sdk_key가 입력되지 않은 상태

'load_fail' : 광고 호출 실패


### 3. 버전 정보 
- 1.4(180323) : 광고 종료 후 현재 액티비티 종료 여부 설정사항 추가 하였습니다. LastscreenFinishCallBack 이 설정되어 있지 않은경우에만 유효 합니다.

