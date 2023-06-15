<template>
  <div :class="{ 'has-logo': showLogo }" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
    <!-- <logo v-if="showLogo" :collapse="isCollapse" /> -->
    <hamburger id="hamburger-container" :is-active="true" class="hamburger-container" @toggleClick="toggleSideBar" :collapse="isCollapse" />

    <el-scrollbar :class="sideTheme" wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground"
        :text-color="sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor"
        :unique-opened="true"
        :active-text-color="theme"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="(route, index) in sidebarRouters"
          :key="route.path + index"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup>
import Logo from './Logo'
import SidebarItem from './SidebarItem'
import variables from '@/assets/styles/variables.module.scss'
import useAppStore from '@/store/modules/app'
import useSettingsStore from '@/store/modules/settings'
import usePermissionStore from '@/store/modules/permission'
import Hamburger from '@/components/Hamburger'

const route = useRoute();
const appStore = useAppStore()
const settingsStore = useSettingsStore()
const permissionStore = usePermissionStore()

const sidebarRouters =  computed(() => [
    {
        "path": "/index/hotel",
        "name": "Hotel",
        "meta": {
            "title": "酒店",
            "icon": "system",
        }
    },
    {
        "path": "/index/train",
        "name": "train",
        "meta": {
          "title": "火车票",
          "icon": "user"
        }
    },
    {
        "path": "/index/attractions",
        "name": "Attractions",
        "meta": {
          "title": "攻略·景点",
          "icon": "user"
        }
    }
]);
console.log(permissionStore.sidebarRouters);
const showLogo = computed(() => settingsStore.sidebarLogo);
// const sideTheme = computed(() => settingsStore.sideTheme);
const sideTheme = "light";
const theme = computed(() => settingsStore.theme);
const isCollapse = computed(() => !appStore.sidebar.opened);

const activeMenu = computed(() => {
  const { meta, path } = route;
  // if set path, the sidebar will highlight the path you set
  if (meta.activeMenu) {
    return meta.activeMenu;
  }
  return path;
})
function toggleSideBar() {
  appStore.toggleSideBar()
}
</script>

<style lang="scss" scoped>
.hamburger-container {
    line-height: 46px;
    height: auto;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }
</style>
