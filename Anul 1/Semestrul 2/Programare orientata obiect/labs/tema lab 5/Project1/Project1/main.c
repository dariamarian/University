#include "Interface/interface.h"
#include "Repozitory/repozitory.h"
#include "Service/service.h"
#include "Validators/validators.h"
#include "Domain/participant.h"
#include "vectordynamic.h"
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>


int main() {
    repo_test();
    valid_test();
    domain_test();
    service_test();
    test_vector();

    repo* repo = repo_initialization();
    service* srv = service_initialization(repo);
    interface* ui = interface_initialization(srv);

    interface_run(ui);
    
    service_destructor(srv);
    interface_destructor(ui);

    _CrtDumpMemoryLeaks();
    return 0;
}
