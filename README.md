# tinker-sample-android
模拟复现因为rx分包导致的应用补丁包crash

复现原因：
1.引入了https://github.com/ceabie/DexKnifePlugin这个分包的库
2.在app/dexknife.txt中配置了rx的依赖类在第二个dex中：
```
-split io.reactivex.**
```
3.随便改一行代码，然后打补丁包，进入使用rx的TestRxZipActivity模块，便会crash
