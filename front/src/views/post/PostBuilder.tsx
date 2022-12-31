import React, {FC, SyntheticEvent, useCallback, useEffect, useState} from "react";
import {
    Box, BoxProps,
    Button,
    Flex,
    FormControl,
    FormLabel,
    forwardRef,
    Heading,
    Input,
    Textarea,
    Text,
    InputGroup, useToast, Select
} from "@chakra-ui/react";
import {BoardPickDto} from "../../services/requests/BoardRequests";
import BoardService from "../../services/BoardService";
import PostService from "../../services/PostService";
import TagInput from "../../components/TagInput";
import {useNavigate} from "react-router-dom";

const PostBuilder: FC = () => {

    const toast = useToast()
    const navigate = useNavigate()

    const [title, setTitle] = useState<string | null>("")
    const [text, setText] = useState<string | null>("")
    const [image, setImage] = useState<File | null>(null)
    const [boardId, setBoardId] = useState("")
    const [tags, setTags] = useState<string[]>([])

    const handleTagsChange = useCallback((event: SyntheticEvent, tags: string[]) => {
        setTags(tags)
    }, [])

    const [boardsToPick, setBoardsToPick] = useState<BoardPickDto[] | null>(null)

    const createPost = () => {
        if (boardId === "") {
            toast({
                title: 'Please, select board.',
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        if (image == null) {
            toast({
                title: 'Please choose an image.',
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        if (tags.length === 0) {
            toast({
                title: 'You have to enter atleast one tag to create a post.',
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        if (title?.length === 0) {
            setTitle(null)
        }

        if (text?.length === 0) {
            setText(null)
        }

        PostService.create({
            boardId: +boardId,
            title,
            text,
            tags,
        }, image)
            .then((req) => {
                if (req.request.status === 201) {
                    toast({
                        title: 'Successfully created a post.',
                        status: 'success',
                        duration: 5000,
                        isClosable: true,
                    })
                    navigate("/post/" + req.data.id)
                    return
                } else {
                    toast({
                        title: 'Error occurred during creating post!',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    })
                    toast({
                        title: req.request.response,
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    })
                    return
                }
            })
    }

    useEffect(() => {
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
        //     style={{
        //     display: "flex",
        //         alignItems: "center",
        //         justifyContent: "center",
        //         flexDirection: "column",
        //         marginTop: "300px"
        // }}
        //     <Box alignItems={"center"}>
        //         <form>
        //             <FormControl>
        //                 <FormLabel>Title</FormLabel>
        //                 <Input placeholder="Title" onChange={(e) => {
        //                     setTitle(e.target.value)
        //                 }}/>
        //             </FormControl>
        //         </form>
        //     </Box>
        <Flex width="full" align="center" justifyContent="center">
            <Box p={2} mt={100}>
                <Box textAlign="center">
                    <Heading>Create a Post</Heading>
                </Box>
                <Box my={4} textAlign="left">
                    <form>

                        <FormControl>
                            <FormLabel>Title</FormLabel>
                            <Input placeholder="Title" onChange={(e) => {
                                setTitle(e.target.value)
                            }}/>
                        </FormControl>

                        <FormControl mt={6}>
                            <FormLabel>Text</FormLabel>
                            <Textarea placeholder="Text"/>
                        </FormControl>

                        <FormControl isRequired mt={6}>
                            <FormLabel>Board</FormLabel>
                            <Select placeholder="Select board" onChange={(e) => { setBoardId(e.target.selectedOptions[0].id) }}>
                                {boardsToPick == null ? null : (
                                    boardsToPick.map((board) => {
                                        return (
                                            <option id={board.id.toString()} key={board.id}>{board.name}</option>
                                        )
                                    })
                                )}
                            </Select>
                        </FormControl>

                        <FormControl isRequired mt={6}>
                            <FormLabel htmlFor="writeUpFile">Image</FormLabel>
                            <InputGroup>
                                {/*<InputLeftElement*/}
                                {/*    pointerEvents="none"*/}
                                {/*    children={<Icon />}*/}
                                {/*/>*/}
                                {/*<Input type='file' style={{ display: 'none' }} ref={inputRef}></Input>*/}
                                <Input
                                    accept={".jpg,.jpeg,.png"}
                                    placeholder={"Pick a file" || "Your file ..."}
                                    type={"file"}
                                    onChange={(e) => {
                                        if (e.target.files != null) {
                                            if (e.target.files.length === 1) {
                                                setImage(e.target.files[0])
                                            }
                                        }
                                    }}
                                    // value={value}
                                />
                            </InputGroup>
                        </FormControl>

                        <FormControl mt={6}>
                            <FormLabel>Tags</FormLabel>
                            <Text mb={3}>📢 press 'enter' to enter tags</Text>
                            <TagInput tags={tags} onTagsChange={handleTagsChange} wrapProps={{ width: "400px" }}/>
                        </FormControl>
                        <Button width="full" mt={4} onClick={() => createPost()} colorScheme={"blue"}>
                            Create
                        </Button>
                    </form>
                </Box>
            </Box>
        </Flex>
    )
}

export default PostBuilder

const PreviewImage = forwardRef<BoxProps, typeof Box>((props, ref) => {
    return (
        <Box
            bg="white"
            top="0"
            height="100%"
            width="100%"
            position="absolute"
            borderWidth="1px"
            borderStyle="solid"
            rounded="sm"
            borderColor="gray.400"
            backgroundSize="cover"
            backgroundRepeat="no-repeat"
            backgroundPosition="center"
            backgroundImage={`url("https://image.shutterstock.com/image-photo/paella-traditional-classic-spanish-seafood-600w-1662253543.jpg")`}
            {...props}
            ref={ref}
        />
    );
});
