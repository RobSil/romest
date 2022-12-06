package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository: JpaRepository<Post, Long> {

    fun findAllByBoard(board: Board): List<Post>

    @Query("select p from Post p where p.board.id = :boardId")
    fun findAllByBoardId(boardId: Long): List<Post>

    @Query("select p from Post p where p.board.id = :boardId")
    fun findAllByBoardIdPageable(boardId: Long, pageable: Pageable): Page<Post>

    // find all posts by tags, sort (prioritize?) by user's tags, and it shouldn't be the posts in the private boards | post.board.isPrivate = false
    // given: list of tags ids, user id
    @Query("select post from Post post left join fetch post.tags tag left join fetch post.board board where ((post.board.user.id <> :userId) and (post.board.isPrivate = false)) order by case when tag.id in :tags then 1 else 0 end")
    fun findAllByTagsRelevant(tags: List<Long>, userId: Long): List<Post>

    @Query("select post from Post post left join fetch post.tags tag left join fetch post.board board where post.board.isPrivate = false order by case when tag.id in :tags then 1 else 0 end")
    fun findAllByTagsRelevant(tags: List<Long>): List<Post>

}
