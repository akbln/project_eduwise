import React, { FC } from 'react'
import './index.css'
import { Tooltip } from 'react-tooltip'



const AchievementCard = ({question}) => {
    return (
        <div className='achievement-card flex justify-center'>
            <Tooltip id="my-tooltip" className='tooltip' />
            question.id
        </div>
    )
}

export default AchievementCard