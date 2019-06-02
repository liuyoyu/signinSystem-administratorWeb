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
    private String status;
    List<MenuTreeDTO> children = new ArrayList<>();

    public MenuTreeDTO() {
    }

    public MenuTreeDTO(Menu menu) {
        this.menuId = menu.getId();
        this.id = menu.getMenuValue();
        this.label = menu.getMenuName();
        this.status = menu.getMenuStatus();
    }

    public MenuTreeDTO(Long menuId, String id, String label, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MenuTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeDTO> children) {
        this.children = children;
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