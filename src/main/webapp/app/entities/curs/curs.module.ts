import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PublicpmrSharedModule } from 'app/shared/shared.module';
import { CursComponent } from './curs.component';
import { CursDetailComponent } from './curs-detail.component';
import { CursUpdateComponent } from './curs-update.component';
import { CursDeleteDialogComponent } from './curs-delete-dialog.component';
import { cursRoute } from './curs.route';

@NgModule({
  imports: [PublicpmrSharedModule, RouterModule.forChild(cursRoute)],
  declarations: [CursComponent, CursDetailComponent, CursUpdateComponent, CursDeleteDialogComponent],
  entryComponents: [CursDeleteDialogComponent]
})
export class PublicpmrCursModule {}
