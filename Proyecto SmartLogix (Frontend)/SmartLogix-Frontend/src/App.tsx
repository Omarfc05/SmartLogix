import { Route, Routes} from "react-router-dom"
import { Home } from "./pages/Home"
import { Layout } from "./layout/Layout" 
import { Test } from "./pages/Test"
import { Products } from "./pages/Products"

function App() {

  return (
    <>
      <Routes>
        <Route element={<Layout />}>
        <Route path="/" element={<Home />}/>
        <Route path="/test" element={<Test />}/>
        <Route path="/products" element={<Products />}/>
        </Route>
      </Routes>
    </>
  )
}

export default App
