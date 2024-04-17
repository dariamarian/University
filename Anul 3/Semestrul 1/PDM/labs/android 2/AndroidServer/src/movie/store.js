import dataStore from "nedb-promise";

class movieItemStore {
  constructor({filename,autoload}) {
    this.store=dataStore({filename,autoload});
}

async find(param){
    return this.store.find(param);
}
async findOne(param){
    return this.store.findOne(param);
}
async insert(movie){

    if (movie.name.length < 1)
        throw new Error('Name must be at least 1 character');
    return this.store.insert(movie);
}
async update(param,movie){
    return this.store.update(param,movie);
    //return 1;
}
async remove(param){
    return this.store.remove(param);
}
}

export const movieStore=new movieItemStore({filename:'./db/movies.json',autoload:true});
