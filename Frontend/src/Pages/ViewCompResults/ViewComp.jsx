import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import styles from "./ViewComp.module.css"
import Header from "../../components/Header/Header.jsx";
import Stat from "../../components/Stats/Stat.jsx";
const ViewComp = () => {
    return(
        <div className={styles.page}>
            <Sidebar role={"teacher"}/>
            <div className={styles.content}>
                <Header/>
                <div className={styles.results}>
                    <Stat type={"comp"}/>
                </div>
            </div>
        </div>
    )
}
export default ViewComp