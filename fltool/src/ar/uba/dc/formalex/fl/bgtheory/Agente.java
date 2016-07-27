package ar.uba.dc.formalex.fl.bgtheory;

import java.util.HashSet;
import java.util.Set;

/**
 * User: P_BENEDETTI
 * Date: 03/12/13
 * Time: 16:36
 */
@SuppressWarnings({"NonFinalFieldReferenceInEquals", "NonFinalFieldReferencedInHashCode"})
public class Agente {
    private String name;
    private Set<Role> roles = new HashSet<Role>();

    public Agente(String nombre) {
        this.name = nombre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }


    public String getRolesCSV() {
        if (roles == null || roles.size() == 0)
            return "";
        StringBuilder res = new StringBuilder();
        for (Role role : roles) {
            res.append(role.getName()).append(", ");
        }
        return res.substring(0, res.length() - 2);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean hasRole(Role role) {
        return this.roles != null && this.roles.contains(role);
    }

    public boolean realizaAccion(Action action) {
        if (action.isImpersonal())
            return false;
        Set<Role> performableBy = action.getPerformableBy();
        //Si no es una 'impersonal action' y  no se pone el keyword 'only performable by'
        //(performableBy.size() = 0) en una acción entonces la puede ejecutar cualquier agente.
        if (roles != null && performableBy != null) {
            if (performableBy.size() == 0)
                return true;
            for (Role rol : roles) {
                if (performableBy.contains(rol))
                    return true;
            }
        }
        return false;
    }

    //devuelve true si el agente realiza alguna de las acciones
    public boolean realizaAlgunaAccion(Set<Action> acciones) {
        for (Action a : acciones) {
            if (realizaAccion(a))
                return true;
        }
        return false;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Agente agente = (Agente) o;

        if (name != null ? !name.equals(agente.name) : agente.name != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public Agente clonar() {
        Agente agenteClonado = new Agente(this.getName());
        agenteClonado.setRoles(this.getRoles());

        return agenteClonado;
    }
}