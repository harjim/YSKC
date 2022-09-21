using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class employeebirthday : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "birthday",
                table: "employee",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "birthday",
                table: "employee");
        }
    }
}
