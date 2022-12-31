import {FC} from "react";
import {ChakraProps, HTMLChakraProps, Icon, IconProps, Image, ImageProps} from "@chakra-ui/react";
import HeartSvg from "../static/heart.svg"

export interface HeartProps extends Omit<IconProps, "width" | "height"> {
    width: number | null,
    height: number | null
}


const Heart: FC<HeartProps> = (props) => {

    const defaultWidth = 16
    const defaultHeight = 16

    return (
        <div style={{mask: HeartSvg}}>
        </div>
    )
}

export default Heart
