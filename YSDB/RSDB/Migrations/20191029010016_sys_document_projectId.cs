using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class sys_document_projectId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<short>(
                name: "month",
                table: "sys_document",
                nullable: false,
                defaultValue: (short)0);

            migrationBuilder.AddColumn<int>(
                name: "projectId",
                table: "sys_document",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<byte>(
                name: "projectType",
                table: "sys_document",
                nullable: false,
                defaultValue: (byte)0);

            migrationBuilder.AddColumn<string>(
                name: "remark",
                table: "sys_document",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<short>(
                name: "year",
                table: "sys_document",
                nullable: false,
                defaultValue: (short)0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "month",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "projectId",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "projectType",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "remark",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "year",
                table: "sys_document");
        }
    }
}
