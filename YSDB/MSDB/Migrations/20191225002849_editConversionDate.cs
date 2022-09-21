using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class editConversionDate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "conversionDate",
                table: "customer");

            migrationBuilder.AddColumn<DateTime>(
                name: "conversionDate",
                table: "h3_customer",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "conversionDate",
                table: "h3_customer");

            migrationBuilder.AddColumn<DateTime>(
                name: "conversionDate",
                table: "customer",
                nullable: true);
        }
    }
}
