package com.liuzk.prism.data.repository

import android.util.Log
import com.liuzk.prism.data.model.Experience
import com.liuzk.prism.data.remote.ApiService
import javax.inject.Inject

class ExperienceRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getExperiences(page: Int): List<Experience> {
        val experiences = apiService.getExperiences(page = page)
        // 增强数据：为title和avatarUrl生成测试数据
        return experiences.map { experience ->
            experience.copy(
                title = generateTitle(experience.username, experience.id),
                avatarUrl = generateAvatarUrl(experience.username, experience.id),
                likes = generateLikes(experience.id)
            )
        }
    }

    /**
     * 基于作者名和ID生成标题
     */
    private fun generateTitle(author: String, id: String): String {
        val titles = listOf(
            "Beautiful Landscape",
            "Stunning Nature View",
            "Amazing Photography",
            "Incredible Moment",
            "Wonderful Scene",
            "Breathtaking View",
            "Perfect Shot",
            "Memorable Experience",
            "Artistic Capture",
            "Inspiring Image"
        )
        // 使用ID的hashCode来选择标题，确保同一ID总是得到相同的标题
        val index = kotlin.math.abs(id.hashCode()) % titles.size
        return "${titles[index]} by $author"
    }

    /**
     * 生成头像URL - 使用UI Avatars API生成基于作者名的头像
     * 这是一个免费的API，可以根据文本生成PNG格式的头像
     */
    private fun generateAvatarUrl(author: String, id: String): String {
        // 获取作者名的首字母作为头像文本
        val initials = author.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.uppercaseChar() }
            .joinToString("")
            .take(2)
            .ifEmpty { author.firstOrNull()?.uppercaseChar()?.toString() ?: "U" }

        // 使用UI Avatars生成头像，基于作者名生成颜色确保一致性
        val nameHash = author.hashCode()
        val textColor = "FFFFFF" // 白色文字

        return "https://ui-avatars.com/api/?name=$initials&size=100&background=random&color=$textColor&bold=true"
    }

    /**
     * 基于ID生成点赞数，确保同一ID总是得到相同的点赞数
     */
    private fun generateLikes(id: String): Int {
        // 使用ID的hashCode生成一个在合理范围内的点赞数
        return kotlin.math.abs(id.hashCode()) % 1000 + 10
    }

}
