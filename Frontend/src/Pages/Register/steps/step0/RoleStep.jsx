import "./roleStep.css"

const RoleStep = ({setStep,setRole}) => {
    const changeRoleToTeacher = () => {
        setRole("teacher");
        setStep(1);
    }
    const changeRoleToStudent = () => {
        setRole("student");
        setStep(1);
    }
    return (
        <div className="role-step-wrapper">
            <div className="role-selector-p row">
                <p>What are you registering as?</p>
            </div>
            <div className={"role-selector-wrapper row"}>
                <div onClick={changeRoleToStudent} className={"role-student col-6"}>
                    <div className={"role-student-text row"}>
                        <p>Student</p>
                    </div>
                    <div className={"role-student-image-wrapper row"}>
                        <div className={"role-student-image"}></div>
                    </div>
                </div>
                <div onClick={changeRoleToTeacher} className={"role-teacher col-6"}>
                    <div className={"role-teacher-text row col-12"}>
                        <p>Teacher</p>
                    </div>
                    <div className={"role-teacher-image-wrapper row col-12"}>
                        <div className={"role-teacher-image"}></div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default RoleStep;