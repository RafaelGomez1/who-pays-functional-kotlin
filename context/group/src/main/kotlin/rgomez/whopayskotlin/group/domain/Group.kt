package rgomez.whopayskotlin.group.domain

import rgomez.whopayskotlin.event.Aggregate
import rgomez.whopayskotlin.members.domain.MemberId

data class Group(
    val id: GroupId,
    val name: GroupName,
    val members: List<MemberId>
) : Aggregate() {

    companion object {
        fun create(
            id: GroupId,
            name: GroupName,
            members: List<MemberId>
        ) = Group(id, name, members)
        // TODO -> add event
    }

    fun Group.pushGroupCreatedEvent(): Nothing = TODO("Not implemented yet")
}



@JvmInline
value class GroupName(val value: String)

@JvmInline
value class GroupId(val value: String) {
    init {
        require(regex.matches(value))
    }

    companion object {
        private val regex = "/^\\{?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\u200C\u200B\\}?\$/".toRegex()
    }
}




