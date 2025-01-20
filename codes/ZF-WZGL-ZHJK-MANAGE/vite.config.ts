import path from "path";
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
// https://vitejs.dev/config/
export default defineConfig({
  publicPath: "./",
  plugins: [vue()],
  server: {
    // ← ← ← ← ← ←
    host: "0.0.0.0", // ← 新增内容 ←
  }, // ← ← ← ← ← ←
  resolve: {
    "@": path.resolve(__dirname, "src"),
  },
});
