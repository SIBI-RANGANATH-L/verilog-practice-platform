import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const Content = () => {
  const [problems, setProblems] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/problems')
      .then((response) => response.json())
      .then((data) => setProblems(data))
      .catch((err) => console.log('Error while fetching problems: ', err));
  }, []);

  return (
    <div className='content'>
      <h1>Problems</h1>
      {problems.length === 0 ? (
        <p>No Problems</p>
      ) : (
        <ul>
          {problems.map((p) => (
            <li key={p.id}>
              <Link to={`/problems/${p.id}`}>
                <strong>{p.title}</strong> — {p.difficulty}
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Content;
