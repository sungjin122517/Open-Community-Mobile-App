package com.example.finalproject.data.service.impl

//@Singleton
//class UserServiceImpl @Inject constructor(
//    private val firestore: FirebaseFirestore,
//    private val auth: AuthService,
//): UserService  {
//    override val user: Flow<User?>
//        get() = firestore.collection(USER_COLLECTION).document(auth.currentUser?.uid!!).dataObjects<User>()
////    override suspend fun getSavedPostId(): List<String> {
//////        return user.onEach {
//////            it.savedPostIds
//////        }.
////    }
//
//    override suspend fun getUser(userId: String): Flow<User?> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun save(user: User): String {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun update(updateMap: Map<String, Any>) {
//        firestore.collection(USER_COLLECTION).document(auth.currentUser?.uid!!).update(updateMap).await()
//    }
//
//    override suspend fun delete(user: User) {
//        TODO("Not yet implemented")
//    }
//    companion object {
//        private const val USER_ID_FIELD = "userId"
//        private const val USER_COLLECTION = "users"
//    }
//}