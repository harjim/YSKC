using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_docFile_dataLengthAndItemsCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "filledItems",
                table: "p_docFile_data",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "totalItems",
                table: "p_docFile_data",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "wordLength",
                table: "p_docFile_data",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "filledItems",
                table: "p_docFile_data");

            migrationBuilder.DropColumn(
                name: "totalItems",
                table: "p_docFile_data");

            migrationBuilder.DropColumn(
                name: "wordLength",
                table: "p_docFile_data");
        }
    }
}
