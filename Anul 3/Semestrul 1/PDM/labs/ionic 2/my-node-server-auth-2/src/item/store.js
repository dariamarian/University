import dataStore from 'nedb-promise';

export class ItemStore {
    constructor({filename, autoload}) {
        this.store = dataStore({filename, autoload});
    }

    async find(props) {
        return this.store.find(props);
    }

    async findOne(props) {
        return this.store.findOne(props);
    }

    async insert(item) {
        return this.store.insert(item);
    };

    async update(props, item) {
        return this.store.update(props, item);
    }

    async remove(props) {
        return this.store.remove(props);
    }
}

export default new ItemStore({filename: './db/items.json', autoload: true});