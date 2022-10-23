package com.robsil.posts

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Photo
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.data.repository.PostRepository
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.model.enum.StoringSource
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.model.exception.constant.PostExceptionMessages
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.service.PhotoService
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.UserService
import com.robsil.mainservice.service.impl.PostServiceImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.util.*

@Tag("unitTest")
@DisplayName("Post Service Tests")
class PostServiceTests {

    val postRepository: PostRepository = mockk()
    val boardService: BoardService = mockk()
    val photoService: PhotoService = mockk()
    val userService: UserService = mockk()

    val postService: PostService = PostServiceImpl(postRepository, boardService, photoService, userService)

    @Nested
    inner class GetById {
        @Test
        fun `test get by id found`() {

            val user = User(1, "email@some.com", "123", false)

            val board = Board(1, "board1", false, user)

            val photo = Photo(1, "/path", StoringSource.FILE_SYSTEM)

            val expect = Post(1, "mocktitle", "mocktext", board, photo)

            every { postRepository.findById(1) } returns Optional.of(Post(1, "mocktitle", "mocktext", board, photo))

            val res = postService.getById(1)

            assertEquals(expect, res)
        }

        @Test
        fun `test get by id not found throw exception`() {

            every { postRepository.findById(1) } returns Optional.empty<Post>()

            val exception = assertThrows<NotFoundException> {
                postService.getById(1)
            }

            assertEquals(exception.message, PostExceptionMessages.POST_NOT_FOUND)
        }


    }

    @Nested
    inner class Create {
        @Test
        fun `empty create`() {
            assertEquals(1, 1)
        }

//        @Test
//        fun `some test create`() {
//
//            val user = User(1, "email@some.com", "123", false)
//
//            val board = Board(1, "board1", false, user)
//            every { boardService.getById(1) } returns board
//
//            val photo = Photo(1, "/path", StoringSource.FILE_SYSTEM)
//            every { photoService.getById(1) } returns photo
//
//            val expect = Post(1, "mocktitle", "mocktext", board, photo)
//
//            postService.create(PostCreateDto(board.id!!, photo.id!!, "sometitle", "sometext"))
//        }
    }
}