using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addPatentReceiptNoAndExpiryDateColumn : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "receiptNo",
                table: "patent_cost",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "expiryDate",
                table: "patent",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "receiptNo",
                table: "patent_cost");

            migrationBuilder.DropColumn(
                name: "expiryDate",
                table: "patent");
        }
    }
}
