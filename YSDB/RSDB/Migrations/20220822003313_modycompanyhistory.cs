using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class modycompanyhistory : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "endTime",
                table: "c_name_history");

            migrationBuilder.RenameColumn(
                name: "beginTime",
                table: "c_name_history",
                newName: "startDate");

            migrationBuilder.AddColumn<DateTime>(
                name: "docDate",
                table: "p_docFile",
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "month",
                table: "c_year_cost",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "docDate",
                table: "p_docFile");

            migrationBuilder.RenameColumn(
                name: "startDate",
                table: "c_name_history",
                newName: "beginTime");

            migrationBuilder.AlterColumn<int>(
                name: "month",
                table: "c_year_cost",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "endTime",
                table: "c_name_history",
                nullable: true);
        }
    }
}
