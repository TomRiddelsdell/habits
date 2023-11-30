import React, { useState, useEffect } from "react";
import "./App.css";
import "@aws-amplify/ui-react/styles.css";
import { API } from "aws-amplify";
import {
  Button,
  Flex,
  Heading,
  Text,
  TextField,
  View,
  withAuthenticator,
} from "@aws-amplify/ui-react";
import { listHabits } from "./graphql/queries";
import {
  createHabit as createHabitMutation,
  deleteHabit as deleteHabitMutation,
} from "./graphql/mutations";

const App = ({ signOut }) => {
  const [notes, setHabits] = useState([]);

  useEffect(() => {
    fetchHabits();
  }, []);

  async function fetchHabits() {
    const apiData = await API.graphql({ query: listHabits });
    const notesFromAPI = apiData.data.listHabits.items;
    setHabits(notesFromAPI);
  }

  async function createHabit(event) {
    event.preventDefault();
    const form = new FormData(event.target);
    const data = {
      name: form.get("name"),
      description: form.get("description"),
    };
    await API.graphql({
      query: createHabitMutation,
      variables: { input: data },
    });
    fetchHabits();
    event.target.reset();
  }

  async function deleteHabit({ id }) {
    const newHabits = notes.filter((note) => note.id !== id);
    setHabits(newHabits);
    await API.graphql({
      query: deleteHabitMutation,
      variables: { input: { id } },
    });
  }

  return (
    <View className="App">
      <Heading level={1}>My Habits App</Heading>
      <View as="form" margin="3rem 0" onSubmit={createHabit}>
        <Flex direction="row" justifyContent="center">
          <TextField
            name="name"
            placeholder="Habit Name"
            label="Habit Name"
            labelHidden
            variation="quiet"
            required
          />
          <TextField
            name="description"
            placeholder="Habit Description"
            label="Habit Description"
            labelHidden
            variation="quiet"
            required
          />
          <Button type="submit" variation="primary">
            Create Habit
          </Button>
        </Flex>
      </View>
      <Heading level={2}>Current Habits</Heading>
      <View margin="3rem 0">
        {notes.map((note) => (
          <Flex
            key={note.id || note.name}
            direction="row"
            justifyContent="center"
            alignItems="center"
          >
            <Text as="strong" fontWeight={700}>
              {note.name}
            </Text>
            <Text as="span">{note.description}</Text>
            <Button variation="link" onClick={() => deleteHabit(note)}>
              Delete note
            </Button>
          </Flex>
        ))}
      </View>
      <Button onClick={signOut}>Sign Out</Button>
    </View>
  );
};

export default withAuthenticator(App);
