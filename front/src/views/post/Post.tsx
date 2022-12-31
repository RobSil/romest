import React, {FC, useEffect, useState} from "react";
import PostService from "../../services/PostService";
import {useNavigate, useParams} from "react-router-dom";
import {ComplexPostDto} from "../../services/requests/PostRequest";
import {AspectRatio, Avatar, Box, Button, Icon, Image, Select, Stack, Text, useToast} from "@chakra-ui/react";
import {BoardPickDto} from "../../services/requests/BoardRequests";
import BoardService from "../../services/BoardService";
import Heart from "../../components/Heart";
import {PhoneIcon} from "@chakra-ui/icons";


const Post: FC = () => {

    const navigate = useNavigate()
    const toast = useToast()

    const postId: string | undefined = useParams().postId

    const [boardId, setBoardId] = useState("")

    const [post, setPost] = useState<ComplexPostDto | null>(null)
    const [isLiked, setIsLiked] = useState(false)
    const [boardsToPick, setBoardsToPick] = useState<BoardPickDto[] | null>(null)

    const repinPost = () => {
        if (postId === undefined) {
            toast({
                title: 'Post is unavailable.',
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        if (boardId === "") {
            toast({
                title: 'Board is invalid.',
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        PostService.repin(+postId, +boardId)
            .then((req) => {
                if (req.request.status === 200) {
                    toast({
                        title: 'Successfully repined a post.',
                        status: 'success',
                        duration: 5000,
                        isClosable: true,
                    })
                    navigate("/post/" + req.data.id)
                    return
                }
            })
    }

    const likePost = () => {
        if (postId === undefined) {
            toast({
                title: 'Post is invalid.',
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        PostService.toggleLike(+postId)
            .then((req) => {
                if (req.request.status === 200) {
                    toast({
                        title: req.data.isLiked ? 'You liked a post!' : 'You disliked a post!',
                        status: 'success',
                        duration: 5000,
                        isClosable: true,
                    })
                    setIsLiked(req.data.isLiked)
                    return
                } else {
                    toast({
                        title: 'Something gone wrong during liking process!',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    })
                    return
                }
            })
    }

    useEffect(() => {
        PostService.getById(+postId!)
            .then(req => {
                if (req.request.status === 200) {
                    setPost(req.data)
                    setIsLiked(req.data.isLiked)
                } else {
                    toast({
                        title: 'Error occurred during fetching a post.',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    })
                    return
                }
            })

        BoardService.getAllForPick()
            .then((req) => {
                if (req.request.status === 200) {
                    setBoardsToPick(req.data)
                } else {
                    toast({
                        title: 'Error occurred during fetching boards to pick.',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    })
                    return
                }
            })
    }, [])

    return (
        <>
            <div style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                flexDirection: "column",
                marginTop: "300px"
            }}>
                {post == null ? null : (
                    <>
                        <Box display={"flex"} flexDirection={"row"}>
                            <Image src={post.photo.imageKitUrl} alt="image" width={"500px"} height={"500px"} />

                            <Stack ml="25px" direction={"column"}>

                                <Box>
                                    <Text>Author: <strong>{post.author.username}</strong></Text>
                                    <Box display={"flex"} flexDirection={"row"} alignItems={"center"}>
                                        <Avatar src={post.author.avatar.imageKitUrl} mr={"10px"}/>
                                        <a>{post.author.username}</a>
                                    </Box>
                                </Box>

                                <Box display={"flex"} flexDirection={"row"}>
                                    <Select placeholder="Select board" onChange={(e) => { setBoardId(e.target.selectedOptions[0].id) }}>
                                        {boardsToPick == null ? null : (
                                            boardsToPick.map((board) => {
                                                return (
                                                    <option id={board.id.toString()} key={board.id}>{board.name}</option>
                                                )
                                            })
                                        )}
                                    </Select>

                                    <Button ml={"15px"} borderRadius={"full"} onClick={(e) => repinPost()}>Save</Button>
                                </Box>

                                <Box display={"flex"} flexDirection={"row"} alignItems={"center"}>
                                    {/*<Icon viewBox="0 0 50 50" width={12} height={12} onClick={() => likePost()} cursor={"pointer"}>*/}
                                    {/*<Icon width={50} height={50} onClick={() => likePost()} cursor={"pointer"}>*/}
                                    {/*    <g xmlns="http://www.w3.org/2000/svg" id="heart">*/}
                                    {/*        <path d="M0 200 v-200 h200      a100,100 90 0,1 0,200     a100,100 90 0,1 -200,0     z"/>*/}
                                    {/*    </g>*/}
                                    {/*    <use xmlns="http://www.w3.org/2000/svg"*/}
                                    {/*         xmlnsXlink="http://www.w3.org/1999/xlink" xlinkHref="#heart"*/}
                                    {/*         style={{stroke: "none", strokeWidth: 0}} fill="red" transform="rotate(225,150,121)"/>*/}
                                    {/*    /!*<path d="M25 39.7l-.6-.5C11.5 28.7 8 25 8 19c0-5 4-9 9-9 4.1 0 6.4 2.3 8 4.1 1.6-1.8 3.9-4.1 8-4.1 5 0 9 4 9 9 0 6-3.5 9.7-16.4 20.2l-.6.5zM17 12c-3.9 0-7 3.1-7 7 0 5.1 3.2 8.5 15 18.1 11.8-9.6 15-13 15-18.1 0-3.9-3.1-7-7-7-3.5 0-5.4 2.1-6.9 3.8L25 17.1l-1.1-1.3C22.4 14.1 20.5 12 17 12z"/>*!/*/}
                                    {/*</Icon>*/}
                                    {/*<Heart onClick={() => likePost()} cursor={"pointer"} width={1} height={1} mr={1} fill={"red"} />*/}
                                    <PhoneIcon onClick={() => likePost()} cursor={"pointer"} width={8} height={8} mr={1} color={isLiked ? "red" : "black"} />
                                    <Text fontWeight={"bold"} onClick={() => likePost()} cursor={"pointer"} color={isLiked ? "red" : "black"}>Like</Text>
                                </Box>

                            </Stack>
                        </Box>
                        {/*<AspectRatio maxW={"500px"} ratio={5 / 4}>*/}
                        {/*</AspectRatio>*/}
                    </>
                )}

            </div>
        </>
    )
}

export default Post
