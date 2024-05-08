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
        <div className={"role-step-wrapper"}>
            <h1>Register as?</h1>
            <div className={"role-wrapper"}>
                <div className={"student-wrapper"} onClick={changeRoleToStudent}>
                    <p>Student</p>
                    <div className={"student-logo"} ></div>
                </div>
                <div className={"teacher-wrapper"} onClick={changeRoleToTeacher}>
                    <p>Teacher</p>
                    <div className={"teacher-logo"} ></div>
                </div>
            </div>
        </div>
    )
}
export default RoleStep;