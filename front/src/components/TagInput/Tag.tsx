import { useCallback } from 'react'
import type { MouseEvent, SyntheticEvent } from 'react'

import { Tag, TagLabel, TagCloseButton } from '@chakra-ui/react'
import type { TagProps, TagLabelProps, TagCloseButtonProps } from '@chakra-ui/react'

export type TagInputTagProps = TagProps & {
    children: string
    onRemove?(event: SyntheticEvent): void

    tagLabelProps?: TagLabelProps
    tagCloseButtonProps?: TagCloseButtonProps
}

export default function TagInputTag({
                                              children,
                                              onRemove,

                                              tagLabelProps,
                                              tagCloseButtonProps,

                                              ...props
                                          }: TagInputTagProps) {
    const onTagCloseButtonClick = tagCloseButtonProps?.onClick
    const handleClickTagCloseButton = useCallback(
        (event: MouseEvent<HTMLButtonElement>) => {
            onTagCloseButtonClick?.(event)
            if (event.isDefaultPrevented()) return

            onRemove?.(event)
        },
        [onRemove, onTagCloseButtonClick]
    )
    return (
        <Tag {...props}>
            <TagLabel {...tagLabelProps}>{children}</TagLabel>
            <TagCloseButton {...tagCloseButtonProps} onClick={handleClickTagCloseButton} />
        </Tag>
    )
}
