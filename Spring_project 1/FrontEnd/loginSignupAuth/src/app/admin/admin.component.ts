import { Component, OnInit } from '@angular/core';
import { User } from '../Model/userModel';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css',
})
export class AdminComponent implements OnInit {
  userDetails: User | undefined;

  ngOnInit(): void {
    this.userDetails = JSON.parse(
      window.sessionStorage.getItem('userDetails')!
    );
  }
}
