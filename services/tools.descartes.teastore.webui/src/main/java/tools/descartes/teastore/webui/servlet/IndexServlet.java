/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tools.descartes.teastore.webui.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.descartes.research.faasteastorelibrary.interfaces.persistence.CategoryEntity;
import tools.descartes.research.faasteastorelibrary.requests.ResponseObject;
import tools.descartes.research.faasteastorelibrary.requests.category.GetAllCategoriesRequest;
import tools.descartes.teastore.registryclient.loadbalancers.LoadBalancerTimeoutException;
import tools.descartes.teastore.registryclient.rest.LoadBalancedImageOperations;
import tools.descartes.teastore.registryclient.rest.LoadBalancedStoreOperations;
import tools.descartes.teastore.entities.ImageSizePreset;
import tools.descartes.teastore.webui.authentication.AuthenticatorSingleton;

/**
 * Servlet implementation for the web view of "Index".
 *
 * @author Andre Bauer
 */
@WebServlet( "/index" )
public class IndexServlet extends AbstractUIServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet( )
    {
        super( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleGETRequest( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException, LoadBalancerTimeoutException
    {
        checkforCookie( request, response );
//        request.setAttribute( "CategoryList",
//                LoadBalancedCRUDOperations.getEntities( Service.PERSISTENCE, "categories", Category.class, -1, -1 ) );
        request.setAttribute( "CategoryList", getAllCategories( ) );
        request.setAttribute( "title", "TeaStore Home" );
        request.setAttribute( "login", isLoggedIn( ) );
//        request.setAttribute( "storeIcon",
//                LoadBalancedImageOperations.getWebImage( "icon", ImageSizePreset.ICON.getSize( ) ) );

        request.getRequestDispatcher( "WEB-INF/pages/index.jsp" ).forward( request, response );
    }

    private List< CategoryEntity > getAllCategories( )
    {
        return new GetAllCategoriesRequest( 0, 10 ).performRequest( ).getEntity( );
    }

    private boolean isLoggedIn( )
    {
        return AuthenticatorSingleton.getInstance( ).isUserLoggedIn( );
    }
}