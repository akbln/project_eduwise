import styles from "./ClassDiv.module.css"
import defaultImage from "../../assets/ClassHeader.png";





const ClassDiv = ({schoolClassInfo}) => {

    return (
        <div className={styles.schoolClass}>
            <div
                className={styles.classHeader}
                style={{
                    backgroundImage: schoolClassInfo.blob
                        ? `url(${schoolClassInfo.blob})`
                        : 'linear-gradient(to right, #fd9627, #fe9a21, #fe9e19, #ffa20f, #ffa600)' // Specified gradient
                }}
            >
                <p>{schoolClassInfo.name}</p>
            </div>
            <div className={styles.classFooter}></div>
        </div>
    );

}
export default ClassDiv