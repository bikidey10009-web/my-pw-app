# LiveReply AI — Native Android starter project

This Android project wraps the existing LiveReply AI web app in a WebView and adds
user-approved Android MediaProjection screen capture with a persistent notification.

## Important limitations
- This starter requests Android's official screen-capture permission and starts a visible foreground service.
- Captured frames are immediately released. They are NOT stored, uploaded, OCR-processed, or sent to an AI model.
- MCQ detection/answer suggestions remain in the existing web app and are not automatically driven by captured frames yet.
- Screen capture may be blocked by Android secure windows, DRM-protected content, or device policies.
- Test on a real Android device. This source has not been compiled or device-tested in this environment.

## Cloud build using GitHub Actions
1. Create a new GitHub repository.
2. Upload the contents of this folder to the repository root.
3. Open the repository's Actions tab.
4. Run **Build Android APK** (or push to `main`).
5. Open the completed workflow run, find **Artifacts**, and download `LiveReplyAI-debug-apk`.
6. Extract the APK and install it on your Android phone. Android may ask you to allow installation from that source.

For a production release, create a signed release APK/AAB and review app privacy disclosures.

## Local build
Requires Android SDK, JDK 17, and Gradle 8.9+:
`gradle assembleDebug`

The debug APK is created at:
`app/build/outputs/apk/debug/app-debug.apk`
