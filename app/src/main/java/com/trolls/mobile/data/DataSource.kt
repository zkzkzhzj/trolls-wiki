package com.trolls.mobile.data

import com.trolls.mobile.R
import com.trolls.mobile.domain.LiveComment
import com.trolls.mobile.domain.TrollSummoner

class DataSource {
    fun loadTrollSummonerData(): List<TrollSummoner> {
        return listOf(
            TrollSummoner(
                R.drawable.leona,
                "Hide on bush",
                setOf<String>("악성대리", "고의트롤"),
                R.drawable.ic_launcher_foreground,
            ),
            TrollSummoner(
                R.drawable.leona,
                "병주바보바보",
                setOf<String>("가스라이팅", "욕설"),
                R.drawable.ic_launcher_foreground,
            ),
            TrollSummoner(
                R.drawable.leona,
                "cabo",
                setOf<String>("탈주", "욕설", "가스라이팅", "잠수"),
                R.drawable.ic_launcher_foreground,
            ),
            TrollSummoner(
                R.drawable.leona,
                "닧읷묷슧",
                setOf<String>("여미새", "잠수", "탈주", "악성대리", "고의트롤"),
                R.drawable.ic_launcher_foreground,
            ),
            TrollSummoner(
                R.drawable.leona,
                "사이퍼즈",
                setOf<String>("욕설", "잠수", "하하"),
                R.drawable.ic_launcher_foreground,
            ),
            TrollSummoner(
                R.drawable.leona,
                "레오나장인",
                setOf<String>("욕설", "테스트"),
                R.drawable.ic_launcher_foreground,
            ),
        )
    }

    fun loadTrollCommentData(): List<LiveComment> {
        return listOf(
            LiveComment(
                "shhhhhhhhhhhh",
                1,
                "고의 트롤 욕설 정치 ㄹㅇ 개에바",
            ),
            LiveComment(
                "Hide on bush",
                1,
                "하앙 팬이에염 >_<~!!",
            ),
            LiveComment(
                "천하무적파괘왕",
                1,
                "저는 두 눈을 의심했습니다.",
            ),
            LiveComment(
                "응애유저",
                1,
                "Fuck User",
            ),
            LiveComment(
                "협곡의마술사뮤진스",
                1,
                "🚝모자마술 ㅋㅋㅋㄹㅇㅋㅋ",
            ),
            LiveComment(
                "트롤유저",
                2,
                "닉값 하는놈",
            ),
            LiveComment(
                "나는진짜쓰레기다",
                3,
                "분리수거 안됨",
            ),
            LiveComment(
                "나는진짜쓰레기다",
                3,
                "분리수거 안됨",
            ),
            LiveComment(
                "바보다",
                3,
                "분리수거 안됨4",
            ),
            LiveComment(
                "응애헌터",
                3,
                "마지막 전 채팅",
            ),
            LiveComment(
                "캬하하하하하ㅏ",
                3,
                "이게 마지막이녗ㅊ",
            ),
        )
    }
}
