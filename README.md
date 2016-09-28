# vocabulary_android
<p>a app for vocabulary study.</p>
<p>这里只是Android客户端代码，服务端代码使用java servlet,代码见vocabulary_server</p>

---项目过程---

2016.09.23之前
<p>完成登录、注册、获取词汇列表功能.</p>

2016.09.23
<p>完成从服务器获取图片.</p>
<p>参考: <a href="http://blog.csdn.net/guolin_blog/article/details/17482165">Android Volley完全解析(二)，使用Volley加载网络图片</a> </p>
<p>对文章中使用的三种Volley获取图片的方式进行简单封装.但是,后来因为缓存问题,最终使用了Picasso来加载图片。</p>

2016.09.24
<p>主要添加了VocabularyActivity, VocabularyFragment，完成词汇列表.</p>
<p>参考：<a href="https://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout">Handling Scrolls with CoordinatorLayout</a> </p>
<p>实现Toolbar的添加，折叠等效果</p>

2016.09.25
<p>主要完成从服务器获取词汇列表.</p>
<p>服务端代码vocabulary_server已上传.</p>

2016.09.26
<p>为实现图片上传做技术准备.</p>

2016.09.27
<p>总算是实现了图片上传,打算整理一个专门用来处理http post工具包，明天尝试.</p>
