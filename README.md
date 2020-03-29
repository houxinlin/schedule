# 小安课表
一款精美Android课程表。实现课程增加、删除、修改，增加计划等功能

### APP体验地址
(已上架酷安，名称为小安课表)

http://dl-cdn.coolapkmarket.com/down/apk_upload/2020/0321/app-release-257979-o_1e3tvolos1m881jfkmv1qm114v42n-uid-1411991.apk?_upt=ca3cde241585488207

### 效果图
![](http://image.coolapk.com/apk_image/2020/0321/15/_20200321152403-257979-o_1e3tvkucu1pn21jjt1t0nk6fts81u-uid-1411991@1080x2248.jpg.t.jpg)

![](http://image.coolapk.com/apk_image/2020/0321/15/_20200321152407-257979-o_1e3tvkucuf9qkiaemvs2ibd01v-uid-1411991@1080x2248.jpg.t.jpg)

### 使用方法

直接在xml中引入ClassScheduleCard即可
```xml
 <com.hxl.course.widget.ClassScheduleCard
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:fitsSystemWindows="true">
 </com.hxl.course.widget.ClassScheduleCard>
```
### 左右上下滑动
如果使用过这款APP，可以发现操作方法可以同时左右，上滑动，这都是由MulitViewGroup来管理
```xml
<com.hxl.course.widget.viewgroup.MulitViewGroup
            android:id="@+id/mulit"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

</com.hxl.course.widget.viewgroup.MulitViewGroup>
```
### 个人公众号
![](https://raw.githubusercontent.com/houxinlin/schedule/master/schedule/app/src/main/res/drawable/qr.webp)

