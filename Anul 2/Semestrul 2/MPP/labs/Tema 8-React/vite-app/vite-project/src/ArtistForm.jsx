import React from  'react';
import { useState } from 'react';
export default function ArtistForm({addFunc}){


    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');


   function handleSubmit (event){

        let artist={
            firstName:firstName,
            lastName:lastName
        }
        console.log('An artist was submitted: ');
        console.log(artist);
        addFunc(artist);
        event.preventDefault();
        setFirstName('')
        setLastName('')
    }
    return (
        <form className="artist-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="firstName">first name:</label>
            <input
              type="text"
              id="firstName"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              className="input-field"
            />
            <label htmlFor="lastName">last name:</label>
            <input
              type="text"
              id="lastName"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              className="input-field"
            />
          </div>
          <button type="submit" className="add-user-button">
            add user
          </button>
        </form>
      );
      
}