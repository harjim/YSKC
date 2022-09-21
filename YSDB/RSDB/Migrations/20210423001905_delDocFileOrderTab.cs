using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class delDocFileOrderTab : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "docFileOrder");

            migrationBuilder.DropColumn(
                name: "stages",
                table: "docFile");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "stages",
                table: "docFile",
                maxLength: 50,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "docFileOrder",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    docFileId = table.Column<int>(nullable: false),
                    order = table.Column<int>(nullable: false),
                    stageKey = table.Column<string>(maxLength: 20, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_docFileOrder", x => x.id);
                });
        }
    }
}
