import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Header from './Header';
import Content from './Content';
import Footer from './Footer';
import ProblemPage from './ProblemPage'; // example new page

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Content />} />
        <Route path="/problems/:id" element={<ProblemPage />} />
         <Route path="*" element={<p>Page not found</p>} />
      </Routes>
      <Footer />
    </Router>
  );
}

export default App;
