import { Component, OnInit } from '@angular/core';
import { User } from '../Model/userModel';
import { CommonModule } from '@angular/common';
import { AuthDataService } from '../Service/authDataService';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css',
})
export class AdminComponent implements OnInit {
  userDetails: User | undefined;

  //allUsersWithRollUserDetails: User[] | undefined | null;

  //storing a hash table because it easer to lookup and remove
  //storage here is not a problem as the user database here will be limited to 10 O(n) n = 10
  allUserDetailsObject: Map<number, User> = new Map();

  constructor(
    private authDataService: AuthDataService,
    private dataService: AuthDataService
  ) {}

  ngOnInit(): void {
    //the subscription for get request is done in the welcome component if admin is logged in
    //this is so that data will be available in the admin component

    this.authDataService.allUsersData.subscribe((allUserData) => {
      allUserData?.forEach((value, count, fullArray) => {
        this.allUserDetailsObject?.set(value.id, value);
      });
    });

    this.userDetails = JSON.parse(
      window.sessionStorage.getItem('userDetails')!
    );
  }

  userIdToRemove(id: number) {
    this.dataService.deleteAUser(id).subscribe({
      next: (sMessage) => {
        console.log(sMessage);
        this.allUserDetailsObject.delete(id);
      },
      error: (eMessage) => {
        console.log('Error when deleting occurred');
        console.log(eMessage);
      },
    });
    //delete a user by there hash code
  }
}
