import React, {useState} from 'react';
import './ArtistsApp.css'

function ArtistRow({artist, deleteFunc, updateFunc}){


    const [updatedFirstName, setUpdatedFirstName] = useState(artist.firstName);
    const [updatedLastName, setUpdatedLastName] = useState(artist.lastName);
    const [isEditing, setIsEditing] = useState(false);

    function handleDelete(){
        console.log('delete button pentru '+artist.id);
        deleteFunc(artist.id);
    }
    function handleUpdate(){
        artist.firstName=updatedFirstName;
        artist.lastName=updatedLastName;
        console.log('update button pentru '+artist.id);
        updateFunc(artist);
    }
    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleCancel = () => {
        setIsEditing(false);
        setUpdatedFirstName(artist.firstName);
        setUpdatedLastName(artist.lastName);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        handleUpdate();
        setIsEditing(false);
    };

    return (
        <tr className={isEditing ? 'editing' : ''}>
          <td>
            {isEditing ? (
              <input
                type="text"
                value={updatedFirstName}
                onChange={(e) => setUpdatedFirstName(e.target.value)}
                className="edit-input"
              />
            ) : (
              artist.firstName
            )}
          </td>
          <td>
            {isEditing ? (
              <input
                type="text"
                value={updatedLastName}
                onChange={(e) => setUpdatedLastName(e.target.value)}
                className="edit-input"
              />
            ) : (
              artist.lastName
            )}
          </td>
          <td className="actions">
            {isEditing ? (
              <>
                <button onClick={handleSubmit} className="save-button">
                  save
                </button>
                <button onClick={handleCancel} className="cancel-button">
                  cancel
                </button>
              </>
            ) : (
              <>
                <button onClick={handleDelete} className="delete-button">
                  delete
                </button>
                <button onClick={handleEdit} className="update-button">
                  update
                </button>
              </>
            )}
          </td>
        </tr>
      );
      
}

export default function ArtistTable({artists, deleteFunc, updateFunc}){
    console.log("In ArtistTable");
    console.log(artists);
    let rows = [];
    artists.forEach(function(artist) {
        rows.push(<ArtistRow artist={artist} key={artist.id} deleteFunc={deleteFunc} updateFunc={updateFunc} />);
    });
    return (
        <div className="ArtistTable">

            <table className="center">
                <thead>
                <tr>
                    <th>first name</th>
                    <th>last name</th>

                    <th>actions</th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>

        </div>
    );
}