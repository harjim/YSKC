using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterP_budgetReturnOld : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "data",
                table: "p_budget");

            migrationBuilder.RenameColumn(
                name: "totalBudget",
                table: "p_budget",
                newName: "value");

            migrationBuilder.AddColumn<string>(
                name: "key",
                table: "p_budget",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "month",
                table: "p_budget",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "stage",
                table: "p_budget",
                maxLength: 10,
                nullable: true);

            migrationBuilder.AddColumn<byte>(
                name: "type",
                table: "p_budget",
                nullable: false,
                defaultValue: (byte)0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "key",
                table: "p_budget");

            migrationBuilder.DropColumn(
                name: "month",
                table: "p_budget");

            migrationBuilder.DropColumn(
                name: "stage",
                table: "p_budget");

            migrationBuilder.DropColumn(
                name: "type",
                table: "p_budget");

            migrationBuilder.RenameColumn(
                name: "value",
                table: "p_budget",
                newName: "totalBudget");

            migrationBuilder.AddColumn<string>(
                name: "data",
                table: "p_budget",
                type: "text",
                nullable: true);
        }
    }
}
