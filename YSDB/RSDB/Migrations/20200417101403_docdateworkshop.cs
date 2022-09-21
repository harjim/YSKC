using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class docdateworkshop : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "docDate",
                table: "sys_document",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "workshopId",
                table: "sys_document",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "docDate",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "workshopId",
                table: "sys_document");
        }
    }
}
