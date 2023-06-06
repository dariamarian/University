import React, { useState, useEffect } from 'react';
import './ArtistsApp.css';
import ArtistTable from './ArtistTable.jsx';
import ArtistForm from './ArtistForm.jsx';
import { GetArtists, AddArtist, UpdateArtist, DeleteArtist } from './utils/REST-calls.js';

export default function ArtistsApp() {
  const [artists, setArtists] = useState([]);

  useEffect(() => {
    fetchArtists();
  }, []);

  async function fetchArtists() {
    try {
      const artists = await GetArtists();
      setArtists(artists);
    } catch (error) {
      console.log('Error fetching artists:', error);
    }
  }

  async function addFunc(artist) {
    console.log('inside addFunc:', artist);
    try {
      await AddArtist(artist);
      await fetchArtists();
    } catch (error) {
      console.log('Error adding artist:', error);
    }
  }

  async function updateFunc(artist) {
    console.log('inside updateFunc:', artist);
    try {
      await UpdateArtist(artist);
      await fetchArtists();
    } catch (error) {
      console.log('Error updating artist:', error);
    }
  }

  async function deleteFunc(artist) {
    console.log('inside deleteFunc:', artist);
    try {
      await DeleteArtist(artist);
      await fetchArtists();
    } catch (error) {
      console.log('Error deleting artist:', error);
    }
  }

  return (
    <div className="ArtistApp">
        <div className="form-section">
          <ArtistForm addFunc={addFunc} />
        </div>
        <div className="table-section">
          <ArtistTable artists={artists} deleteFunc={deleteFunc} updateFunc={updateFunc} />
        </div>
      </div>
  );
  
}
