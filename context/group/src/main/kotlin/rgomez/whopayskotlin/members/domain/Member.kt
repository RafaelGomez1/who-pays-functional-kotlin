package rgomez.whopayskotlin.members.domain

data class Member(
    val id: MemberId,
    val name: MemberName
)

@JvmInline
value class MemberId(val value: String)

@JvmInline
value class MemberName(val value: String)
