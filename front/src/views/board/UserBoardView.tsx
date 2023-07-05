import {FC, useEffect, useState} from "react";
import BoardService from "../../services/BoardService";
import {useParams} from "react-router-dom";
import PostService from "../../services/PostService";
import {SimpleBoardDto} from "../../services/requests/BoardRequests";
import {ComplexPostDto, ComplexPostPageableDto} from "../../services/requests/PostRequest";
import PostCard from "../post/PostCard";
import {Box} from "@chakra-ui/react";
import ReactPaginate from "react-paginate";

export enum SpecialUserBoardView {
    DEFAULT, ALL_POSTS, LIKED, RELEVANT
}

export interface UserBoardViewProps {
    isSpecial: SpecialUserBoardView
}

const UserBoardView: FC<UserBoardViewProps> = (props) => {

    const {username, minimizedName} = useParams()

    const [board, setBoard] = useState<SimpleBoardDto | null>(null)
    const [postPageable, setPostPageable] = useState<ComplexPostPageableDto | null>(null)

    const [pageNumber, setPageNumber] = useState(0)
    const [pageSize, setPageSize] = useState(20)

    const fetchPosts = () => {
        switch (props.isSpecial) {
            case SpecialUserBoardView.DEFAULT:
                PostService.getByUsernameAndBoardMinimizedName(username!, minimizedName!, pageNumber, pageSize)
                    .then(req => {
                        if (req.request.status === 200) {
                            setPostPageable(req.data)
                        }
                    })
                break
            case SpecialUserBoardView.ALL_POSTS:
                PostService.getAllByUsername(username!, pageNumber, pageSize)
                    .then(req => {
                        if (req.request.status === 200) {
                            setPostPageable(req.data)
                        }
                    })
                break
            case SpecialUserBoardView.LIKED:
                PostService.getAllLikedByUsername(username!, pageNumber, pageSize)
                    .then(req => {
                        if (req.request.status === 200) {
                            setPostPageable(req.data)
                        }
                    })
                break
            case SpecialUserBoardView.RELEVANT:
                PostService.getAllRelevant(pageNumber, pageSize)
                    .then(req => {
                        if (req.request.status === 200) {
                            setPostPageable(req.data)
                        }
                    })

        }
    }

    useEffect(() => {
        if (props.isSpecial != SpecialUserBoardView.DEFAULT) {
            BoardService.getByUsernameAndMinimizedName(username!, minimizedName!)
                .then(req => {
                    if (req.request.status === 200) {
                        setBoard(req.data)
                    }
                })
        } else {
            setBoard({
                minimizedName: props.isSpecial.toString(),
                name: props.isSpecial.toString(),
                isPrivate: true, id: 0
            })
        }
    }, [])

    useEffect(() => {
        fetchPosts()
    }, [pageNumber])

    return (
        <>
            <h1>Posts:</h1>

            <Box display={"flex"} flexDirection={"row"} flexWrap={"wrap"} gap={"6"}>
                {postPageable != null ? (postPageable.posts.map((post) => {
                    return (<PostCard post={post} cursor={"pointer"}/>)
                })) : null}
            </Box>

            <ReactPaginate pageCount={postPageable ? postPageable.totalPages : 0} onPageChange={(pageNumber) => setPageNumber(pageNumber.selected)} />

        </>
    )
}

export default UserBoardView
