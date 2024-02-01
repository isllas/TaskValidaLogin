package br.com.isllascurty.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.isllascurty.todolist.user.IUserRepository;
//import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltertaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
       
        var servletPath = request.getServletPath();
        if(servletPath.startsWith("/tasks/")){

        //Pegar autenticação (usuario e senha)
             var autorization = request.getHeader("Autorization");
             
             var authEncoded = autorization.substring("Basic".length()).trim();
             
             byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

             var authString = new String(authDecoded);
             
             System.out.println("Autorization");
             System.out.println(authString);

             //[usuario, senha]
             String[] credentials = authString.split(":");
             String username = credentials[0];
             String password = credentials[1];
             System.out.println(autorization);
             System.out.println(username);
             System.out.println(password);

        //Validar usuário
            var user = this.userRepository.findByUsername(username);
             if(user == null)   {
                response.sendError(401, "Usuário sem autorização");

             } else {

             //Validar a senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified){
                    request.setAttribute("idUser", user.getId());
                     filterChain.doFilter(request, response);
                } else{
                    response.sendError(401);
                }
            }
             //Se estiver ok segue o fluxo

                

             } else{
                filterChain.doFilter(request, response);
             }
            

       
    }

    /*Usando o implements Filter ficaria asim
     * 
     * Implements Filter não possui de forma automatica o HTTP request e HTTP Response
     
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
                //Executar alguma ação (doFilter)
                System.out.println("Chegou no Filtro");
                chain.doFilter(request, response);
       
    }
    */
    
}
