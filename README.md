# Prism - 一款现代化的 Android 应用

Prism 是一个示例 Android 应用程序，完全使用 Kotlin 和现代 Android 开发工具构建。它展示了一个清晰、健壮的架构，用于从远程 API 获取数据并以列表形式展示。

## ✨ 功能特性

- **现代化 UI**: 完全由 **Jetpack Compose** 构建，并采用 **Material 3** 风格。
- **远程数据获取**: 从 `picsum.photos` 公共 API 获取图片和作者数据列表。
- **瀑布流布局**: 以精美的垂直滚动瀑布流网格展示内容。
- **无限滚动**: 当用户滚动到列表末尾时，自动加载更多项目。
- **下拉刷新**: 允许用户通过简单的下拉手势刷新数据。
- **动态布局**: 在单列和双列视图之间即时切换。
- **健壮的状态管理**: 利用由 `ViewModel` 管理的单一 `UiState` 对象，确保 UI 状态的可预测性和一致性。
- **完整的用户体验**: 处理所有常见场景，包括初始加载、分页加载、错误状态（带重试选项）和刷新状态。

## 🛠️ 技术栈与核心库

- **UI**: Jetpack Compose, Material 3
- **架构**: MVVM (Model-View-ViewModel), 单向数据流 (UDF)
- **依赖注入**: Hilt
- **网络请求**: Retrofit & OkHttp
- **JSON 解析**: Moshi
- **异步编程**: Kotlin Coroutines & StateFlow
- **图片加载**: Coil

## 🚀 如何运行

1.  克隆此仓库。
2.  在 Android Studio 中打开项目。
3.  等待 Gradle 同步依赖项。
4.  在模拟器或物理设备上构建并运行。
