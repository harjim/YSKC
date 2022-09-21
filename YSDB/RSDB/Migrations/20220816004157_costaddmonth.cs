﻿using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class costaddmonth : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "month",
                table: "c_year_cost",
                nullable: true,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "month",
                table: "c_year_cost");
        }
    }
}
