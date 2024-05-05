import "./SelectFile.css";


function SelectFile({ onFileSelect }) {
    return (
        <div className="select-file">
            <input type="file" id="file" onChange={onFileSelect} style={{display: 'none'}}/>
            <label htmlFor="file" className="btn">Choose File</label>
        </div>
    );
}

export default SelectFile;
