const express = require('express');

const app = express();

const notes = [
    {
        id: "21500679-90e9-4dff-997b-45175c29a6d4",
        title: "Note: not finished",
        description: "Unfinished description of my note",
        email: "john.doe@gmail.com",
        done: false,
    },
    {
        id: "4539f704-17f9-4fc2-b97a-ab00d29ac061",
        title: "Done with this note",
        description: "All done!",
        email: "john.doe@gmail.com",
        done: true,
    },
    {
        id: "7dfe3469-16dd-4459-bab0-b5a71d9d9923",
        title: "Different note",
        description: "Description",
        email: "jane.doe@gmail.com",
        done: true,
    }
]

app.use(express.json());

app.get("/api/notes", (request, response) => {
    response.send(notes)
});

app.get("/api/notes/:email", (request, response) => {
    response.send(notes.filter(it => it.email === request.params.email));
});

app.listen(3000, () => {
    console.log("Server Listening on port:", 3000);
});
