/**
 * @Author liuyoyu
 * @Date:2019/6/2 16:59
 **/
package com.signInStart.Entity.DTO;
import com.signInStart.Entity.Menu;
import java.util.ArrayList;
import java.util.List;

public class MenuTreeDTO {
    private Long menuId; //菜单id
    private String id;    //菜单键值（前端需求）
    private String label; //菜单值名（前端需求）
    private String icon;
    private String URL;
    private Boolean status;
    List<MenuTreeDTO> children = new ArrayList<>();

    public MenuTreeDTO() {
    }

    public MenuTreeDTO(Menu menu) {
        this.menuId = menu.getId();
        this.id = menu.getMenuValue();
        this.label = menu.getMenuName();
        this.status = menu.getMenuStatus();
        this.icon = menu.getIcon();
        this.URL = menu.getMenuURL();
    }

    public MenuTreeDTO(Long menuId, String id, String label, Boolean status) {
        this.menuId = menuId;
        this.id = id;
        this.label = label;
        this.status = status;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<MenuTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeDTO> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setChildrenMenu(List<Menu> menus) {
        List<MenuTreeDTO> list = new ArrayList<>();
        for (Menu m : menus) {
            MenuTreeDTO menuTreeDTO = new MenuTreeDTO(m);
            list.add(menuTreeDTO);
        }
        this.children = list;
    }
}